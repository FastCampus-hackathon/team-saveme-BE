package com.saveme.comparator.domain;

import com.saveme.comparator.dto.JobDataDto;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Entity
public class Wish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wishId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruition_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    private Recruition recruition;

    public static Wish createWish(User user, Recruition recruition){

        return Wish
                .builder()
                .user(user)
                .recruition(recruition)
                .build();
    }

    public void addWishListOfUser(User user){
        this.user = user;
        this.user.getWishList().add(this);
    }

    public void addWishListOfRecruition(Recruition recruition){
        this.recruition = recruition;
        this.recruition.getWishList().add(this);
    }

}
