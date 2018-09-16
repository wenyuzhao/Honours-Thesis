@Inline
public final Address alloc(int bytes, int align, int offset) {
  /* establish how much we need */
  Address start = alignAllocationNoFill(cursor, align, offset);
  Address end = start.plus(bytes);
  /* check whether we've exceeded the limit */
  if (end.GT(limit)) {
    return allocSlowInline(bytes, align, offset);
  }
  /* sufficient memory is available, so we can finish performing the allocation */
  fillAlignmentGap(cursor, start);
  cursor = end;
  // Record the end cursor of this region
  Region.setCursor(currentRegion, cursor);
  return start;
}