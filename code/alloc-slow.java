@Override
protected final Address allocSlowOnce(int bytes, int align, int offset) {
  // Acquire a new region
  Address ptr = space.getSpace(allocationKind);
  this.currentRegion = ptr;

  if (ptr.isZero()) {
    return ptr; // failed allocation --- we will need to GC
  }
  /* we have been given a clean block */
  cursor = ptr;
  limit = ptr.plus(Region.BYTES_IN_REGION);
  return alloc(bytes, align, offset);
}