package kr.or.ddit.member.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.exception.UserNotFoundException;
import kr.or.ddit.login.service.AuthenticateService;
import kr.or.ddit.login.service.AuthenticateServiceImpl;
import kr.or.ddit.member.dao.MemberDAO;
import kr.or.ddit.member.dao.MemberDAOImpl;
import kr.or.ddit.memo.controller.MemoControllerServlet;
import kr.or.ddit.memo.dao.DataBaseMemoDAOImpl;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.PagingVO;

public class MemberServiceImpl implements MemberService {
	
	// 결합력 최상... (안좋은경우)
	private MemberDAO memberDAO = new MemberDAOImpl();
	private AuthenticateService authService = new AuthenticateServiceImpl();
	private static final Logger log = LoggerFactory.getLogger(MemoControllerServlet.class);
	@Override
	public ServiceResult createMember(MemberVO member) {
		
		ServiceResult result = null;
		try {
			retrieveMember(member.getMemId());
			result = ServiceResult.PKDUPLICATED;
			
		}catch (UserNotFoundException e) {
			
			int rowcnt = memberDAO.insertMember(member);
			
			result = rowcnt > 0 ? ServiceResult.OK : ServiceResult.FAIL;

		}
		return result;
		
		}
		

	@Override
	public List<MemberVO> retrieveMemberList(PagingVO<MemberVO> pagingVO) {
		pagingVO.setTotalRecord(memberDAO.selectTotalRecord(pagingVO));
		
		List<MemberVO> memberList = memberDAO.selectMemberList(pagingVO);
		pagingVO.setDataList(memberList);
		log.info("memberList : {}",memberList);
		memberList.stream()
		.forEach(System.out::println); // 메소드 레퍼런스 구조
	
		return memberList;
		
	}

	@Override
	public MemberVO retrieveMember(String memId) {
		MemberVO selectMember = memberDAO.selectMember(memId);
		if(selectMember == null) {
			throw new UserNotFoundException(String.format(memId+"에 해당하는 사용자 없음."));
		}
	
		return selectMember;
	}

	@Override
	public ServiceResult modifyMember(MemberVO member) {
		
		MemberVO inputDate = new MemberVO();
		inputDate.setMemId(member.getMemId());
		inputDate.setMemPass(member.getMemPass());
		
		ServiceResult result = authService.authenticate(inputDate);
		
		if(ServiceResult.OK.equals(result)) {
			int rowcnt = memberDAO.updateMember(member);
			result = rowcnt > 0 ? ServiceResult.OK : ServiceResult.FAIL;

		}
		return result;
		
		
	}

	@Override
	public ServiceResult removeMember(MemberVO member) {
		
		ServiceResult result = authService.authenticate(member);
		
		if(ServiceResult.OK.equals(result)) {
			int rowcnt = memberDAO.deleteMember(member.getMemId());
			result = rowcnt > 0 ? ServiceResult.OK : ServiceResult.FAIL;
			
		}
		return result;
	}

}
