package org.sjimenez.chatapp.model;

import java.util.Objects;

public class UserGroupRelation {

    private int iduser_group;
    private int iduser_user_group;
    private int idgroup_user_group;

    public int getIduser_user_group() {
        return iduser_user_group;
    }

    public void setIduser_user_group(int iduser_user_group) {
        this.iduser_user_group = iduser_user_group;
    }

    public int getIdgroup_user_group() {
        return idgroup_user_group;
    }

    public void setIdgroup_user_group(int idgroup_user_group) {
        this.idgroup_user_group = idgroup_user_group;
    }

    public int getIduser_group() {
        return iduser_group;
    }

    public void setIduser_group(int iduser_group) {
        this.iduser_group = iduser_group;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserGroupRelation that = (UserGroupRelation) o;
        return iduser_group == that.iduser_group &&
                iduser_user_group == that.iduser_user_group &&
                idgroup_user_group == that.idgroup_user_group;
    }
    @Override
    public int hashCode() {
        return Objects.hash(iduser_group, iduser_user_group, idgroup_user_group);
    }

    @Override
    public String toString() {
        return "UserGroupRelation{" +
                "iduser_group=" + iduser_group +
                ", iduser_user_group=" + iduser_user_group +
                ", idgroup_user_group=" + idgroup_user_group +
                '}';
    }
}
