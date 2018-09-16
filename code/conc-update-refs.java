@Override
@Inline
public void processEdge(ObjectReference source, Address slot) {
  ObjectReference oldObject, newObject;
  do {
    oldObject = slot.prepareObjectReference();
    newObject = traceObject(oldObject);
    if (oldObject.toAddress().EQ(newObject.toAddress())) return;
  } while (!slot.attempt(oldObject, newObject));
}
