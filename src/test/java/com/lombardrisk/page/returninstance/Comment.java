package com.lombardrisk.page.returninstance;

import java.util.Optional;

public class Comment {

    private final String comment;
    private final String user;
    private final String action;
    private final String revision;

    public Comment(
            final String comment,
            final String user,
            final String action,
            final String revision) {
        this.comment = comment;
        this.user = user;
        this.action = action;
        this.revision = revision;
    }

    Optional<String> user() {
        return Optional.ofNullable(user);
    }

    Optional<String> comment() {
        return Optional.ofNullable(comment);
    }

    Optional<String> action() {
        return Optional.ofNullable(action);
    }

    Optional<String> revision() {
        return Optional.ofNullable(revision);
    }
}
