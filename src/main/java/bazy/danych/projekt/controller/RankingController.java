package bazy.danych.projekt.controller;

import bazy.danych.projekt.dao.LeagueDao;
import bazy.danych.projekt.dao.RankingDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RankingController {

    private final RankingDao rankingDao;

    private final LeagueDao leagueDao;

    public RankingController(RankingDao rankingDao, LeagueDao leagueDao) {
        this.rankingDao = rankingDao;
        this.leagueDao = leagueDao;
    }

    @RequestMapping("/ranking")
    public String list(Model model) {

        model.addAttribute("list", rankingDao.getAll());

        return "ranking/list";
    }

    @RequestMapping("/ranking/liga")
    public String listLeague(Model model) {

        model.addAttribute("leagues", leagueDao.getAllLeagues());

        return "ranking/leagueList";
    }


    @RequestMapping("/ranking/liga/{id}")
    public String listForLeague(@PathVariable("id") String id, Model model) {

        try {
            model.addAttribute("leagues", leagueDao.getAllLeagues());
            model.addAttribute("leagueList", rankingDao.getForLeague(id));
            model.addAttribute("leagueName", leagueDao.getLeagueById(id).getName());
        } catch (Exception ignored) {
        }

        return "ranking/leagueList";
    }

}
