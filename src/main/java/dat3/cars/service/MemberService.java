package dat3.cars.service;

import dat3.cars.dto.MemberRequest;
import dat3.cars.dto.MemberResponse;
import dat3.cars.entity.Member;
import dat3.cars.repository.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberService {

  private MemberRepository memberRepository;

  public MemberService(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }

  public List<MemberResponse> getMembers(boolean includeAll) {
    List<Member> members = memberRepository.findAll();
    //OLD SCHOOL STYLE
    /* List<MemberResponse> memberResponses = new ArrayList<>();
    for (Member m: members) {
      MemberResponse mr = new MemberResponse(m, includeAll);
      memberResponses.add(mr);
    }
   */
    //LAMBDA METHOD
    return members.stream().map(m -> new MemberResponse(m, includeAll)).toList();
  }

  public MemberResponse addMember(MemberRequest memberRequest){

    if(memberRepository.existsById(memberRequest.getUsername())){
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Member with this ID already exist");
    }
    if(memberRepository.existsByEmail(memberRequest.getEmail())){
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Member with this Email already exist");
    }

    Member newMember = MemberRequest.getMemberEntity(memberRequest);
    newMember = memberRepository.save(newMember);

    return new MemberResponse(newMember, false);
  }

  public MemberResponse getMemberByUsername(String username, boolean includeAll) {
    Member member = memberRepository.findMemberByUsername(username);
    return new MemberResponse(member, includeAll);
  }


  public void deleteByUsername(String username) {
    memberRepository.deleteById(username);
  }

  public ResponseEntity<Boolean> updateMemberDetails(MemberRequest body, String username) {
    Member updateMember = memberRepository.findMemberByUsername(username);
    updateMember.setUsername(body.getUsername());
    updateMember.setEmail(body.getEmail());
    updateMember.setFirstName(body.getFirstName());
    updateMember.setLastName(body.getLastName());
    updateMember.setStreet(body.getStreet());
    updateMember.setCity(body.getCity());
    updateMember.setZip(body.getZip());

    memberRepository.save(updateMember);
    return ResponseEntity.ok(true);
  }

  public void setRankingValue(String username, int value) {
    Member member = memberRepository.findMemberByUsername(username);
    member.setRanking(value);
    memberRepository.save(member);
  }

  public List<MemberResponse> getMembersByFirstName(String firstName, boolean includeAll) {
    List<Member> members = memberRepository.findMembersByFirstName(firstName);

    return members.stream().map(m -> new MemberResponse(m, includeAll)).toList();
  }
}
