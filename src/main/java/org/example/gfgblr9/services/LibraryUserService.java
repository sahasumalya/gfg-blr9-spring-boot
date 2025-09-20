package org.example.gfgblr9.services;

import org.example.gfgblr9.enums.LibraryAssetType;
import org.example.gfgblr9.enums.LibraryUserRoles;
import org.example.gfgblr9.models.AddAssetRequest;
import org.example.gfgblr9.models.AssetResponse;
import org.example.gfgblr9.models.LibraryAsset;
import org.example.gfgblr9.models.LibraryUser;
import org.example.gfgblr9.pubsub.RedisMessagePublisher;
import org.example.gfgblr9.repositories.LibraryAssetRepository;
import org.example.gfgblr9.repositories.LibraryUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class LibraryUserService {
    private LibraryUserRepository libraryUserRepository;
    private LibraryAssetRepository libraryAssetRepository;
    private RedisCacheManager redisCacheManager;
    private RedisTemplate<String, String> stringRedisTemplate2;
    private RedisMessagePublisher publisher;

    @Autowired
    public LibraryUserService(LibraryUserRepository libraryUserRepository, LibraryAssetRepository libraryAssetRepository, RedisCacheManager redisCacheManager, RedisTemplate<String, String> stringRedisTemplate2, RedisMessagePublisher publisher) {
        this.libraryUserRepository = libraryUserRepository;
        this.libraryAssetRepository = libraryAssetRepository;
        this.redisCacheManager = redisCacheManager;
        this.stringRedisTemplate2 = stringRedisTemplate2;
        this.publisher = publisher;

    }

    public LibraryUser register(String username, String password, LibraryUserRoles role) {
        if (libraryUserRepository.findByUsername(username) != null) {
            return null;
        }
        LibraryUser libraryUser = new LibraryUser();
        libraryUser.setUsername(username);
        libraryUser.setPassword(password);
        libraryUser.setRole(role);
        libraryUser.setNickname(UUID.randomUUID().toString());
        libraryUser.setCreated(new Timestamp(System.currentTimeMillis()));
        libraryUserRepository.save(libraryUser);
        return libraryUser;
    }


    public boolean login(String username, String password) {
        LibraryUser libraryUser = libraryUserRepository.findByUsernameAndPassword(username, password);
        return libraryUser != null;
    }

    public String getPassword(String username) {
        //Cache cache = redisCacheManager.getCache(username);
        //if (cache.get("password") != null) {
            //return cache.get("password").get().toString();
       // }
        if (this.stringRedisTemplate2.hasKey(username)) {
            publisher.publish("cache hit for username: " + username);
            return this.stringRedisTemplate2.opsForValue().get(username);
        }
        publisher.publish("cache miss for username: " + username);
        LibraryUser libraryUser = libraryUserRepository.findByUsername(username);
        stringRedisTemplate2.opsForValue().set(username, libraryUser.getPassword());
        //cache.put("password", libraryUser != null ? libraryUser.getPassword() : "");
        return String.valueOf(libraryUser != null ? libraryUser.getPassword() : "");
    }

    public LibraryAsset addAsset(AddAssetRequest addAssetRequest) {
        LibraryUser libraryUser = libraryUserRepository.findByUsername(addAssetRequest.getUsername());
        if(libraryUser != null && libraryUser.getRole().equals(LibraryUserRoles.ADMIN)) {
            LibraryAsset libraryAsset = new LibraryAsset();
            libraryAsset.setAuthor(addAssetRequest.getAuthor());
            libraryAsset.setType(addAssetRequest.getType());
            libraryAsset.setName(addAssetRequest.getName());
            libraryAsset.setContributor(libraryUser);
            libraryAsset.setDescription(addAssetRequest.getDescription());
            libraryAsset.setPublishedAt(new Timestamp(System.currentTimeMillis()));
            //libraryUser.getContributions().add(libraryAsset);

            return libraryAssetRepository.save(libraryAsset);
            //return libraryAsset;
        }
        return null;

    }

    public List<AssetResponse> findAssets(String username) {
        LibraryUser libraryUser = libraryUserRepository.findByUsername(username);
        if(libraryUser != null) {
            List<LibraryAsset> libraryAssets = libraryUser.getContributions();
            List<AssetResponse> assetResponses = new ArrayList<>();
            for (LibraryAsset libraryAsset : libraryAssets) {
                AssetResponse assetResponse = new AssetResponse();
                assetResponse.setAuthor(libraryAsset.getAuthor());
                assetResponse.setType(libraryAsset.getType());
                assetResponse.setName(libraryAsset.getName());
                assetResponse.setDescription(libraryAsset.getDescription());
                assetResponse.setPublishedAt(libraryAsset.getPublishedAt());
                assetResponses.add(assetResponse);
            }
            return assetResponses;
        }
        return null;
    }
}
