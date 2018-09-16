@Inline
public boolean objectReferenceTryCompareAndSwap(ObjectReference src, Address slot, ObjectReference old, ObjectReference tgt, Word metaDataA, Word metaDataB, int mode) {
  boolean result = VM.barriers.objectReferenceTryCompareAndSwap(src, old, tgt, metaDataA, metaDataB, mode);
  if (!result) {
    result = VM.barriers.objectReferenceTryCompareAndSwap(src, getForwardingPointer(old), tgt, metaDataA, metaDataB, mode);
  }
  return result;
}