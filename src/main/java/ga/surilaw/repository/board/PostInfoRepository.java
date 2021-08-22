package ga.surilaw.repository.board;

import ga.surilaw.domain.entity.PostInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostInfoRepository extends JpaRepository<PostInformation, Long>{

}