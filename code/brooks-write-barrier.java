@Inline
public ObjectReference getForwardingPointerOnWrite(ObjectReference object) {
  if (brooksWriteBarrierActive) {
    if (isInCollectionSet(object)) {
      ObjectReference newObject;
      Word priorStatusWord = ForwardingWord.attemptToForward(object);
      if (ForwardingWord.stateIsForwardedOrBeingForwarded(priorStatusWord)) {
        // The object is forwarded by other threads
        newObject = ForwardingWord.spinAndGetForwardedObject(object, priorStatusWord);
      } else {
        // Forward this object before write
        newObject = ForwardingWord.forwardObjectWithinMutatorContext(object, ALLOC_RS);
      }
      return newObject;
    } else {
      return object;
    }
  } else {
    return getForwardingPointer(object);
  }
}

@Inline
public void objectReferenceWrite(ObjectReference src, Address slot, ObjectReference tgt, Word metaDataA, Word metaDataB, int mode) {
  ObjectReference newSrc = getForwardingPointerOnWrite(src);
  VM.barriers.objectReferenceWrite(newSrc, tgt, metaDataA, metaDataB, mode);
}