package com.example.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vo.memberVO;

@Service
public class MemberDao {
		@Autowired //@Bean으로 만들어진 객체를 받아옴.
		private SqlSessionFactory sqlFactory = null;
		
		public int insertMember(memberVO obj) {
			return sqlFactory.openSession().insert("Member.join", obj);
		}
		
		public List<memberVO> selectMemberList() {
			return sqlFactory.openSession().selectList("Member.memberlist");
		}
		public memberVO selectMemberLogin(memberVO obj) {
			return sqlFactory.openSession().selectOne("Member.login", obj);
		}
		public int deleteMemberBatch(String[] userid) {
			return sqlFactory.openSession().delete("Member.delmemberbatch", userid);
		}
}