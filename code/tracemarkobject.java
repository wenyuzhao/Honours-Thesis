@Inline
public ObjectReference traceMarkObject(TransitiveClosure trace, ObjectReference object) {
  if (testAndMark(rtn)) {
    Address region = Region.of(object);
    // Atomically increase the live bytes of this reigon
    Region.updateRegionAliveSize(region, object);
    // Push into the object queue
    trace.processNode(object);
  }
  return object;
}