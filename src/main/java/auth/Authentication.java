package auth;

import etc.FireBase;

public abstract class Authentication {
    private boolean enabled=false;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public abstract boolean enroll();
    public abstract boolean verify(User user);
}
