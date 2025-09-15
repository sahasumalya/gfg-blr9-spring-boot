package org.example.gfgblr9.repositories;

import org.example.gfgblr9.models.LibraryAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface LibraryAssetRepository extends CrudRepository<LibraryAsset, Long> {
}
