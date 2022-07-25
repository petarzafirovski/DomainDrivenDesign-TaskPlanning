package mk.ukim.finki.users.domain.model;

import mk.ukim.finki.sharedkernel.domain.base.DomainObjectId;
import org.springframework.lang.NonNull;

public class UserId  extends DomainObjectId {
    private UserId() {
        super(UserId.randomId(UserId.class).getId());
    }

    public UserId(@NonNull String uuid) {
        super(uuid);
    }

    public static UserId of(String uuid) {
        UserId p = new UserId(uuid);
        return p;
    }
}
