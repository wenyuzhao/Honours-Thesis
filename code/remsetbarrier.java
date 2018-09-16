@Inline
void markAndEnqueueCard(Address card) {
  if (CardTable.attemptToMarkCard(card, true)) {
    remSetLogBuffer().plus(remSetLogBufferCursor << Constants.LOG_BYTES_IN_ADDRESS).store(card);
    remSetLogBufferCursor += 1;
    if (remSetLogBufferCursor >= REMSET_LOG_BUFFER_SIZE) {
      enqueueCurrentRSBuffer(true);
    }
  }
}

@Inline
void checkCrossRegionPointer(ObjectReference src, Address slot, ObjectReference ref) {
  Word x = VM.objectModel.objectStartRef(src).toWord();
  Word y = VM.objectModel.objectStartRef(ref).toWord();
  Word tmp = x.xor(y).rshl(Region.LOG_BYTES_IN_REGION);
  if (!tmp.isZero() && Space.isInSpace(G1.G1, ref)) {
    Address card = Region.Card.of(src);
    markAndEnqueueCard(card);
  }
}