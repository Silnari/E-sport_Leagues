package bazy.danych.projekt.controller;

import bazy.danych.projekt.bean.League;
import bazy.danych.projekt.dao.GameDao;
import bazy.danych.projekt.dao.LeagueDao;
import bazy.danych.projekt.dao.MatchDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LeagueController {

    private final LeagueDao leagueDao;

    private final MatchDao matchDao;

    private final GameDao gameDao;

    public LeagueController(LeagueDao leagueDao, MatchDao matchDao, GameDao gameDao) {
        this.leagueDao = leagueDao;
        this.matchDao = matchDao;
        this.gameDao = gameDao;
    }

    @RequestMapping("/league")
    public String list(Model model) {

        model.addAttribute("list", leagueDao.getAllLeagues());

        return "league/list";
    }

    @RequestMapping("/league/{id}")
    public String show(@PathVariable("id") String id, Model model) {

        League league = leagueDao.getLeagueById(id);

        String leagueName;

        if (league == null) {
            league = new League();
            leagueName = "Nie znaleziono ligii";
        } else leagueName = league.getName();

        model.addAttribute("leagueName", leagueName);
        model.addAttribute("league", league);

        model.addAttribute("leagueMatches", matchDao.getByLeagueId(league.getId()));

        return "league/show";
    }

    @RequestMapping("/league/add")
    public String addLeague(Model model) {

        model.addAttribute("newLeague", new League());
        model.addAttribute("addedMessage", "");

        return "league/add";
    }

    @RequestMapping(value = "/league/add", method = RequestMethod.POST)
    public String addLeaguePost(@ModelAttribute League league, Model model) {

        String gameId;

        try {
            gameId = gameDao.getGameByName(league.getGameName()).getId();
        } catch (Exception e) {
            model.addAttribute("addedMessage", "Podana gra nie znajduję się w bazie");
            model.addAttribute("newLeague", new League());
            return "league/add";
        }

        if (gameId == null || gameId.equals("")) {
            model.addAttribute("addedMessage", "Podana gra nie znajduję się w bazie");
            model.addAttribute("newLeague", new League());
            return "league/add";
        }

        league.setIdGame(gameId);

        int response = leagueDao.addLeague(league);
        if (response == 0)
            model.addAttribute("addedMessage", "Liga została dodana pomyślnie.");
        else model.addAttribute("addedMessage", "Nie udało sie dodać ligi");
        model.addAttribute("newLeague", new League());

        return "league/add";
    }
}
