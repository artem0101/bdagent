package ru.education.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(ClientToSubjectEntity.PK.class)
@Table(
        schema = "realtor_agency",
        name = "client_to_subject"
)
public class ClientToSubjectEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @OneToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "client_id",
            nullable = false,
            referencedColumnName = "id",
            unique = true,
            foreignKey = @ForeignKey(
                    name = "fk_client_to_subject_clients"
            )
    )
    private ClientEntity client;
    @Id
    @OneToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "subject_id",
            nullable = false,
            referencedColumnName = "id",
            unique = true,
            foreignKey = @ForeignKey(
                    name = "fk_client_to_subject_subjects"
            )
    )
    private SubjectEntity subject;
    @Column(
            columnDefinition = "BOOLEAN DEFAULT true"
    )
    private boolean isSubjectOwner;


    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PK implements Serializable {

        @Serial
        private static final long serialVersionUID = 1L;
        private long client;
        private long subject;

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            } else if (o != null && this.getClass() == o.getClass()) {
                PK that = (PK)o;
                return this.getClient() == that.getClient() && this.getSubject() == that.getSubject();
            } else {
                return false;
            }
        }

        public int hashCode() {
            return Objects.hash(this.getClient(), this.getSubject());
        }
    }

}
