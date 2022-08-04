package com.hanghae.helog.repository;

import com.hanghae.helog.domain.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByCreatedAtDesc();
//    Slice<Post> findAllOrderByCreatedAtDesc(Pageable pageable);
//    Page<Post> findAll(Pageable pageable);
    Post findByUrl(String url);
}
