@Inline
public ObjectReference traceEvacuateObject(TraceLocal trace, ObjectReference object, int allocator) {
  if (Region.relocationRequired(Region.of(object))) {
    Word priorStatusWord = ForwardingWord.attemptToForward(object);
    if (ForwardingWord.stateIsForwardedOrBeingForwarded(priorStatusWord)) {
      // This object is forward by other threads
      return ForwardingWord.spinAndGetForwardedObject(object, priorStatusWord);
    } else {
      // Forward this object
      ObjectReference newObject = ForwardingWord.forwardObject(object, allocator);
      trace.processNode(newObject);
      return newObject;
    }
  } else {
    if (testAndMark(object)) {
      trace.processNode(object);
    }
    return object;
  }
}