package dat3.cars.service;

import dat3.cars.dto.MemberRequest;
import dat3.cars.dto.MemberResponse;
import dat3.cars.entity.Member;
import dat3.cars.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
class MemberServiceH2Test {

  @Autowired
  public MemberRepository memberRepository;

  MemberService memberService;

  boolean dataIsReady = false;

  @BeforeEach
  void setUp() {
    if (!dataIsReady) {
      memberRepository.save(new Member("m1", "test12", "m1@a.dk", "aa", "Olsen",
          "xx vej 34", "Lyngby", "2800"));
      memberRepository.save(new Member("m2", "test12", "m2@a.dk", "bb", "hansen", "xx vej 34", "Lyngby", "2800"));
      dataIsReady = true;
      memberService = new MemberService(memberRepository);
    }
  }

  @Test
  void addMember() {
  }

  @Test
  void getMembersAdmin() {
    List<MemberResponse> members = memberService.getMembers(true);
    assertEquals(2, members.size());
    assertNotNull(members.get(0).getCreated());
  }

  @Test
  void getMembersByFirstName() {
    List<MemberResponse> members = memberService.getMembersByFirstName("bb", true);
    assertEquals(1, members.size());
    assertEquals("bb", members.get(0).getFirstName());
  }

  @Test
  void getMemberByUsername() {
    MemberResponse member = memberService.getMemberByUsername("m2", true);
    assertEquals("m2", member.getUsername());
    assertEquals("hansen", member.getLastName());
  }

  @Test
  void updateMemberDetails() {
    MemberResponse member = memberService.getMemberByUsername("m2", true);
    assertNotNull(member);
    assertEquals("hansen", member.getLastName());


    MemberRequest updatedMemberDetails = new MemberRequest();
    updatedMemberDetails.setFirstName("newFirstName");
    updatedMemberDetails.setLastName("newLastName");

    memberService.updateMemberDetails(updatedMemberDetails, "m2");

    MemberResponse updatedMember = memberService.getMemberByUsername("m2", true);


    assertNotNull(updatedMember);
    assertEquals("newFirstName", updatedMember.getFirstName());
    assertEquals("newLastName", updatedMember.getLastName());
  }

  @Test
  void setRankingValue() {
    memberService.setRankingValue("m2", 5);
    MemberResponse member = memberService.getMemberByUsername("m2", true);
    assertEquals(5, member.getRanking());
  }

  @Test
  void deleteByUsername() {
    Member member = memberRepository.save(new Member("deleteTest", "test12", "m1@a.dk", "cc", "Jensen",
        "xx vej 34", "Lyngby", "2800"));
    memberService.deleteByUsername("deleteTest");
    assertFalse(memberRepository.existsById("deleteTest"));
  }
}