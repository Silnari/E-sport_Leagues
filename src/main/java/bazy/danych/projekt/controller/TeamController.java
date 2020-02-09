package bazy.danych.projekt.controller;

import bazy.danych.projekt.bean.Team;
import bazy.danych.projekt.dao.PlayerInTeamDao;
import bazy.danych.projekt.dao.TeamDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TeamController {

    private final TeamDao teamDao;

    private final PlayerInTeamDao playerInTeamDao;

    public TeamController(TeamDao teamDao, PlayerInTeamDao playerInTeamDao) {
        this.teamDao = teamDao;
        this.playerInTeamDao = playerInTeamDao;
    }

    @RequestMapping("/team")
    public String list(Model model) {

        model.addAttribute("list", teamDao.getAllTeams());

        return "team/list";
    }

    @RequestMapping("/team/show")
    public String show(Model model) {

        model.addAttribute("team", new Team());
        model.addAttribute("teamName", "");

        return "team/show";
    }

    @RequestMapping("/team/show/{name}")
    public String showTeam(@PathVariable("name") String name, Model model) {

        Team team = teamDao.getTeamByName(name);

        String teamName;

        if (team == null) {
            team = new Team();
            teamName = "Nie znaleziono podanego zespołu";
        } else teamName = team.getName();

        model.addAttribute("teamName", teamName);
        model.addAttribute("team", team);

        model.addAttribute("playerList", playerInTeamDao.getByTeamId(team.getId()));

        return "team/show";
    }

    @RequestMapping(value = "/team/add", method = RequestMethod.GET)
    public String add(Model model) {

        model.addAttribute("newTeam", new Team());
        model.addAttribute("addedMessage", "");

        return "team/add";
    }

    @RequestMapping(value = "/team/add", method = RequestMethod.POST)
    public String addTeam(@ModelAttribute Team team, Model model) {

        if (team.getNumberOfMatches() == null || team.getNumberOfMatches().equals(""))
            team.setNumberOfMatches("0");

        int response = teamDao.addTeam(team);
        if (response == 0)
            model.addAttribute("addedMessage", "Druzyna została dodana pomyślnie.");
        else model.addAttribute("addedMessage", "Nie udało sie dodać druzyny");
        model.addAttribute("newTeam", new Team());

        return "team/add";
    }

//    @RequestMapping(value = "/team/delete/{name}")
//    public String removeTeam(@PathVariable String name, Model model) {
//
//        try {
//            if (teamDao.getTeamByName(name) == null) {
//                model.addAttribute("teamName", "Nie udało się usunąc zespołu");
//                model.addAttribute("team", new Team());
//                return "team/show";
//            }
//        } catch (Exception e) {
//            model.addAttribute("teamName", "Nie udało się usunąc zespołu");
//            model.addAttribute("team", new Team());
//            return "team/show";
//        }
//
//        int response = teamDao.deleteTeamByName(name);
//
//        String responseMessage;
//
//
//        if (response == 0) responseMessage = "Zespół został pomyślnie usunięty";
//        else responseMessage = "Nie udało się usunąc zespołi";
//
//        model.addAttribute("teamName", responseMessage);
//        model.addAttribute("team", new Team());
//        return "team/show";
//    }
}
