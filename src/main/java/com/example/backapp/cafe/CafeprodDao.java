package com.example.backapp.cafe;

import java.util.ArrayList;

import com.example.backapp.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CafeprodDao extends JpaRepository<Cafeprod, Integer> {
	ArrayList<Cafeprod> findBySeller(Member seller);
}
