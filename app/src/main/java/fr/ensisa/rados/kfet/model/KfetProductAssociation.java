package fr.ensisa.rados.kfet.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;

@Entity(primaryKeys = {"kid","pid"},
        indices = {@Index("kid"), @Index("pid")})
public class KfetProductAssociation {
    public long kid;
    public long pid;

    public KfetProductAssociation() {
    }

    @Ignore
    public KfetProductAssociation(long kid, long pid) {
        this.kid = kid;
        this.pid = pid;
    }
}
