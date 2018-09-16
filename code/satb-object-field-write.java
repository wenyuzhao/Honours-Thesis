@Inline
public void objectReferenceWrite(ObjectReference src, Address slot, ObjectReference tgt, Word metaDataA, Word metaDataB, int mode) {
  if (barrierActive) checkAndEnqueueReference(slot.loadObjectReference());
  VM.barriers.objectReferenceWrite(src, tgt, metaDataA, metaDataB, mode);
}

@Inline
public boolean objectReferenceTryCompareAndSwap(ObjectReference src, Address slot, ObjectReference old, ObjectReference tgt, Word metaDataA, Word metaDataB, int mode) {
  boolean result = VM.barriers.objectReferenceTryCompareAndSwap(src, old, tgt, metaDataA, metaDataB, mode);
  if (barrierActive) checkAndEnqueueReference(old);
  return result;
}

@Inline
public boolean objectReferenceBulkCopy(ObjectReference src, Offset srcOffset, ObjectReference dst, Offset dstOffset, int bytes) {
  Address cursor = dst.toAddress().plus(dstOffset);
  Address limit = cursor.plus(bytes);
  while (cursor.LT(limit)) {
    ObjectReference ref = cursor.loadObjectReference();
    if (barrierActive) checkAndEnqueueReference(ref);
    cursor = cursor.plus(BYTES_IN_ADDRESS);
  }
  return false;
}