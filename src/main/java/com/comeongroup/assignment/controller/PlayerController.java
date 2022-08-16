package com.comeongroup.assignment.controller;

import com.comeongroup.assignment.data.dto.gamedata.GameDataDto;
import com.comeongroup.assignment.data.dto.gamedata.LoveUnlovedDto;
import com.comeongroup.assignment.data.dto.player.CreatePlayerDto;
import com.comeongroup.assignment.data.dto.player.PlayerDto;
import com.comeongroup.assignment.data.dto.player.UpdatePlayerDto;
import com.comeongroup.assignment.service.PlayerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/player")
@Slf4j
public class PlayerController {

    private final PlayerService service;

    public PlayerController(PlayerService service) {
        this.service = service;
    }

    @PostMapping()
    public ResponseEntity<PlayerDto> createPlayer(@Valid @RequestBody CreatePlayerDto createPlayerDTO) {
        PlayerDto playerDto = service.create(createPlayerDTO);
        return ResponseEntity.ok().body(playerDto);
    }

    @PutMapping()
    public ResponseEntity<PlayerDto> updatePlayer(@RequestBody UpdatePlayerDto updatePlayerDTO) {
        PlayerDto playerDto = service.update(updatePlayerDTO);
        return ResponseEntity.ok().body(playerDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerDto> getPlayer(@PathVariable Long id) {
        PlayerDto playerDto = service.findOne(id);
        return ResponseEntity.ok().body(playerDto);
    }

    @GetMapping()
    public ResponseEntity<Page<PlayerDto>> getAllPlayers(Pageable pageable) {
        Page<PlayerDto> players = service.getAll(pageable);
        return ResponseEntity.ok()
                .body(players);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/love-unloved")
    public ResponseEntity<GameDataDto> loveUnloved(@Valid @RequestBody LoveUnlovedDto loveUnlovedDto) {
        service.loveUnloved(loveUnlovedDto);
        return ResponseEntity.noContent().build();
    }
}