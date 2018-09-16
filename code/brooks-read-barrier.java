// class ShenandoahMutator

@Inline
public ObjectReference getForwardingPointer(ObjectReference object) {
  return ForwardingWord.extractForwardingPointer(object);
}

@Inline
public ObjectReference objectReferenceRead(ObjectReference src, Address slot, Word metaDataA, Word metaDataB, int mode) {
  return VM.barriers.objectReferenceRead(getForwardingPointer(src), metaDataA, metaDataB, mode);
}

// class ForwardingWord

@Inline
public static ObjectReference extractForwardingPointer(ObjectReference oldObject) {
  return oldObject.toAddress().loadWord(FORWARDING_POINTER_OFFSET).and(FORWARDING_MASK.not()).toAddress().toObjectReference();
}