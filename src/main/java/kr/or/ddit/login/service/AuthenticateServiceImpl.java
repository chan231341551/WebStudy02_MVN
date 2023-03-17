package kr.or.ddit.login.service;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.exception.UserNotFoundException;
import kr.or.ddit.member.controller.MemberInsertController;
import kr.or.ddit.member.dao.MemberDAO;
import kr.or.ddit.member.dao.MemberDAOImpl;
import kr.or.ddit.vo.MemberVO;

public class AuthenticateServiceImpl implements AuthenticateService {
	
	private MemberDAO MemberDAO = new MemberDAOImpl();
	private static final Logger log = LoggerFactory.getLogger(MemberInsertController.class);
	@Override
	public ServiceResult authenticate(MemberVO member) {
		
		MemberVO savedMember = MemberDAO.selectMember(member.getMemId());
		
		if(savedMember == null || savedMember.isMemDelete()) {
			throw new UserNotFoundException(String.format("%s 사용자 없음.", member.getMemId()));
		}
		
		String inputPass = member.getMemPass();
		String savedPass = savedMember.getMemPass();
		ServiceResult result = null;
		log.info("savedMember : {}",savedMember);
		log.info("member : {}",member);
		
		if(savedPass.equals(inputPass)) {
//			member.setMemName(savedMember.getMemName());
			try {
				BeanUtils.copyProperties(member, savedMember);
			} catch (IllegalAccessException | InvocationTargetException e) {
				
				throw new RuntimeException(e);
			}
			result = ServiceResult.OK;
		}
		else {
			result = ServiceResult.INVALIDPASSWORD;
		}
		
		return result;
	}

}
