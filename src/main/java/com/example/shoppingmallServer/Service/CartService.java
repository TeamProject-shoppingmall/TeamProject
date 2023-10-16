package com.example.shoppingmallServer.Service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.example.shoppingmallServer.Dto.CartDto;
import com.example.shoppingmallServer.Entity.Cart;
import com.example.shoppingmallServer.Entity.Item;
import com.example.shoppingmallServer.Entity.Member;
import com.example.shoppingmallServer.Exception.EmptyValueException;
import com.example.shoppingmallServer.Exception.NotFoundException;
import com.example.shoppingmallServer.JWT.JwtTokenProvider;
import com.example.shoppingmallServer.Repository.CartRepository;
import com.example.shoppingmallServer.Repository.ItemRepository;
import com.example.shoppingmallServer.Repository.MemberRepository;
import com.example.shoppingmallServer.Response.CartResponse;
import com.example.shoppingmallServer.Response.ImageResponse;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Transactional
    public ResponseEntity<String> cartInsert(CartDto cartDto, String accessToken) {
        String idFromToken = jwtTokenProvider.getIdFromToken(accessToken);

        Member findMember = memberRepository.findOneByUserId(idFromToken);
        if (findMember == null) {
            throw new NotFoundException("회원을 찾을 수 없습니다.");
        }

        Item findItem = itemRepository.findOneById(cartDto.getItemKey());
        if (findItem == null) {
            throw new NotFoundException("상품을 찾을 수 없습니다.");
        }

        Cart cart = Cart.createCart(findMember, findItem, cartDto);
        if (cart == null) {
            throw new EmptyValueException("잘못된 입력이 존재합니다.");
        }

        return cartRepository.cartInsert(cart);
    }

    @Transactional
    public ResponseEntity<List<CartResponse>> findOneById(String accessToken) throws IOException {
        String idFromToken = jwtTokenProvider.getIdFromToken(accessToken);
        Member findMember = memberRepository.findOneByUserId(idFromToken);

        List<Tuple> findByCart = cartRepository.findOneById(findMember.getMemberKey());

        if (findByCart == null) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }

        List<CartResponse> cartResponses = new ArrayList<>();

        for (Tuple cart: findByCart) {
            cartResponses.add(new CartResponse(cart));
        }
        return new ResponseEntity<>(cartResponses, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<String> remove(int cartKey, String accessToken) {
        String idFromToken = jwtTokenProvider.getIdFromToken(accessToken);
        Member findMember = memberRepository.findOneByUserId(idFromToken);

        Cart cartById = cartRepository.findCartById(cartKey, findMember.getMemberKey());

        if (cartById == null) {
            throw new NotFoundException("장바구니 정보가 존재하지 않습니다.");
        }
        return cartRepository.remove(cartById);
    }

    @Transactional
    public ResponseEntity<String> removeAll(String accessToken) {
        String idFromToken = jwtTokenProvider.getIdFromToken(accessToken);
        Member findMember = memberRepository.findOneByUserId(idFromToken);

        if (findMember == null) {
            throw new EmptyValueException("회원 정보가 존재하지 않습니다.");
        }

        List<Cart> allById = cartRepository.findAllById(findMember.getMemberKey());

        if (allById == null) {
            throw new NotFoundException("장바구니 정보가 존재하지 않습니다.");
        }
        List<Integer> cartKeys = allById.stream()
                .map(Cart::getCartKey)
                .collect(Collectors.toList());
        return cartRepository.removeAll(cartKeys);
    }
}

