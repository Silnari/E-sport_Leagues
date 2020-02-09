package bazy.danych.projekt.controller;

import bazy.danych.projekt.bean.Player;
import bazy.danych.projekt.dao.PlayerDao;
import bazy.danych.projekt.dao.PlayerInTeamDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PlayerController {

    private final PlayerDao playerDao;

    private final PlayerInTeamDao playerInTeamDao;

    public PlayerController(PlayerDao playerDao, PlayerInTeamDao playerInTeamDao) {
        this.playerDao = playerDao;
        this.playerInTeamDao = playerInTeamDao;
    }

    @RequestMapping("/player")
    public String list(Model model) {

        model.addAttribute("list", playerDao.getAllPlayers());

        return "player/list";
    }

    @RequestMapping("/player/show")
    public String show(Model model) {

        model.addAttribute("player", new Player());
        model.addAttribute("playerName", "");

        return "player/show";
    }


    @RequestMapping("/player/show/{name}")
    public String showPlayer(@PathVariable("name") String name, Model model) {

        Player player = playerDao.getPlayerByNickname(name);

        String playerName;

        if (player == null) {
            player = new Player();
            playerName = "Nie znaleziono podanego gracza";
        } else playerName = player.getNickname();

        model.addAttribute("playerName", playerName);
        model.addAttribute("player", player);

        model.addAttribute("teamList", playerInTeamDao.getByPlayerId(player.getId()));

        return "player/show";
    }

    @RequestMapping(value = "/player/add", method = RequestMethod.GET)
    public String add(Model model) {

        model.addAttribute("newPlayer", new Player());
        model.addAttribute("addedMessage", "");

        return "player/add";
    }

    @RequestMapping(value = "/player/add", method = RequestMethod.POST)
    public String addPlayer(@ModelAttribute Player player, Model model) {

        int response = playerDao.addPlayer(player);
        if (response == 0)
            model.addAttribute("addedMessage", "Gracz został dodany pomyślnie.");
        else model.addAttribute("addedMessage", "Nie udało sie dodać gracza");
        model.addAttribute("newPlayer", new Player());

        return "player/add";
    }

    @RequestMapping(value = "/player/delete/{id}")
    public String removePlayer(@PathVariable String id, Model model) {

        try {
            if (playerDao.getPlayerById(id) == null) {
                model.addAttribute("playerName", "Nie udało się usunąc gracza");
                model.addAttribute("player", new Player());
                return "player/show";
            }
        } catch (Exception e) {
            model.addAttribute("playerName", "Nie udało się usunąc gracza");
            model.addAttribute("player", new Player());
            return "player/show";
        }

        int response = playerDao.deletePlayerById(id);

        String responseMessage;


        if (response == 0) responseMessage = "Gracz został pomyślnie usunięty";
        else responseMessage = "Nie udało się usunąc gracza";

        model.addAttribute("playerName", responseMessage);
        model.addAttribute("player", new Player());
        return "player/show";
    }
}
