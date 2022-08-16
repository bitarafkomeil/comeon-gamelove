package com.comeongroup.assignment.controller;

import com.comeongroup.assignment.config.TopMostLovedProperties;
import com.comeongroup.assignment.data.dto.game.CreateGameDto;
import com.comeongroup.assignment.data.dto.game.GameDto;
import com.comeongroup.assignment.data.dto.game.UpdateGameDto;
import com.comeongroup.assignment.data.dto.gamedata.GameDataDto;
import com.comeongroup.assignment.data.dto.gamedata.MostLovedDto;
import com.comeongroup.assignment.service.GameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/game")
@Slf4j
public class GameController {

    private final GameService service;
    private final TopMostLovedProperties topMostLovedProperties;

    public GameController(GameService service, TopMostLovedProperties topMostLovedProperties) {
        this.service = service;
        this.topMostLovedProperties = topMostLovedProperties;
    }

    @PostMapping()
    public ResponseEntity<GameDto> createGame(@Valid @RequestBody CreateGameDto createGameDTO) {
        GameDto gameDto = service.create(createGameDTO);
        return ResponseEntity.ok().body(gameDto);
    }

    @PutMapping()
    public ResponseEntity<GameDto> updateGame(@RequestBody UpdateGameDto updateGameDTO) {
        GameDto gameDto = service.update(updateGameDTO);
        return ResponseEntity.ok().body(gameDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameDto> getGame(@PathVariable Long id) {
        GameDto gameDto = service.findOne(id);
        return ResponseEntity.ok().body(gameDto);
    }

    @GetMapping()
    public ResponseEntity<Page<GameDto>> getAllGames(Pageable pageable) {
        Page<GameDto> games = service.getAll(pageable);
        return ResponseEntity.ok()
                .body(games);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/data/top")
    public ResponseEntity<List<MostLovedDto>> getMostLovedGames(@RequestParam(required = false) Optional<Integer> count) {
        List<MostLovedDto> gameData = service.getMostLovedGames(count.orElse(topMostLovedProperties.getCount()));
        return ResponseEntity.ok()
                .body(gameData);
    }

    @GetMapping("/data/all")
    public ResponseEntity<Page<GameDataDto>> getAllGameData(@RequestParam(required = false) Optional<Long> playerId, Pageable pageable) {
        Page<GameDataDto> gameData = service.getAllGameData(pageable, playerId);
        return ResponseEntity.ok()
                .body(gameData);
    }
}