@Inline
public boolean objectReferenceCompare(ObjectReference lhs, ObjectReference rhs) {
  if (lhs.toAddress().NE(rhs.toAddress()) && getForwardingPointer(lhs).toAddress().NE(getForwardingPointer(rhs).toAddress())) {
    return false;
  } else {
    return true;
  }
}