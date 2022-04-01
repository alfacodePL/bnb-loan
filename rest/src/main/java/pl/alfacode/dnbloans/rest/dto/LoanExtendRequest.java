package pl.alfacode.dnbloans.rest.dto;

import java.util.Objects;

public class LoanExtendRequest {
    private ExtensionTerm extensionTerm;

    public LoanExtendRequest(ExtensionTerm extensionTerm) {
        this.extensionTerm = extensionTerm;
    }

    public LoanExtendRequest() {
    }

    public ExtensionTerm getExtensionTerm() {
        return extensionTerm;
    }

    public void setExtensionTerm(ExtensionTerm extensionTerm) {
        this.extensionTerm = extensionTerm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoanExtendRequest)) return false;
        LoanExtendRequest that = (LoanExtendRequest) o;
        return extensionTerm == that.extensionTerm;
    }

    @Override
    public int hashCode() {
        return Objects.hash(extensionTerm);
    }
}
