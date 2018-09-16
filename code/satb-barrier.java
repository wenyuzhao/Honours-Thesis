@Override
protected void checkAndEnqueueReference(ObjectReference ref) {
  if (!barrierActive || ref.isNull()) return;

  if (Space.isInSpace(Regional.RS, ref)) Regional.regionSpace.traceMarkObject(remset, ref);
  else if (Space.isInSpace(Regional.IMMORTAL, ref)) Regional.immortalSpace.traceObject(remset, ref);
  else if (Space.isInSpace(Regional.LOS, ref)) Regional.loSpace.traceObject(remset, ref);
  else if (Space.isInSpace(Regional.NON_MOVING, ref)) Regional.nonMovingSpace.traceObject(remset, ref);
  else if (Space.isInSpace(Regional.SMALL_CODE, ref)) Regional.smallCodeSpace.traceObject(remset, ref);
  else if (Space.isInSpace(Regional.LARGE_CODE, ref)) Regional.largeCodeSpace.traceObject(remset, ref);
}

