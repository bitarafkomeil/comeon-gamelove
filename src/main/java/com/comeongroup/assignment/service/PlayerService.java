package com.comeongroup.assignment.service;

import com.comeongroup.assignment.data.dto.gamedata.LoveUnlovedDto;
import com.comeongroup.assignment.data.dto.player.CreatePlayerDto;
import com.comeongroup.assignment.data.dto.player.PlayerDto;
import com.comeongroup.assignment.data.dto.player.UpdatePlayerDto;
import com.comeongroup.assignment.data.model.Game;
import com.comeongroup.assignment.data.model.GameData;
import com.comeongroup.assignment.data.model.Player;
import com.comeongroup.assignment.data.repository.GameDataRepository;
import com.comeongroup.assignment.data.repository.PlayerRepository;
import com.comeongroup.assignment.exception.BadRequestException;
import com.comeongroup.assignment.mapper.PlayerMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlayerService extends CrudService<Player, PlayerRepository,
        CreatePlayerDto, UpdatePlayerDto, PlayerDto, PlayerMapper> {
    private final GameDataRepository gameDataRepository;
    private final GameService gameService;

    public PlayerService(PlayerRepository repository, PlayerMapper mapper, GameDataRepository gameDataRepository, GameService gameService) {
        super(repository, mapper);
        this.gameDataRepository = gameDataRepository;

        this.gameService = gameService;
    }

    @Override
    protected void preCreate(Player entity) {
        if (repository.existsByName(entity.getName()))
            throw new BadRequestException("Duplicate Player Name");
    }

    @Override
    protected void preDelete(Long id) {
        if (gameDataRepository.existsByPlayerId(id))
            throw new BadRequestException("Player Has GameData,Delete Game Data Before Deleting The Player ");
    }

    public void loveUnloved(LoveUnlovedDto loveUnlovedDto) {
        Game game = gameService.validateAndGet(loveUnlovedDto.getGameId());
        Player player = validateAndGet(loveUnlovedDto.getPlayerId());
        Optional<GameData> gameDataOptional = gameDataRepository.findByGameIdAndPlayerId(loveUnlovedDto.getGameId(), loveUnlovedDto.getPlayerId());
        if (gameDataOptional.isPresent())
            gameDataRepository.delete(gameDataOptional.get());
        else {
            GameData gameData = new GameData();
            gameData.setGame(game);
            gameData.setPlayer(player);
            gameDataRepository.save(gameData);
        }
    }
}