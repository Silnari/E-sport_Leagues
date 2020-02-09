package bazy.danych.projekt.controller;

import bazy.danych.projekt.bean.League;
import bazy.danych.projekt.bean.Rule;
import bazy.danych.projekt.bean.Tournament;
import bazy.danych.projekt.dao.GameDao;
import bazy.danych.projekt.dao.MatchDao;
import bazy.danych.projekt.dao.RulesDao;
import bazy.danych.projekt.dao.TournamentDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TournamentController {

    private final TournamentDao tournamentDao;

    private final MatchDao matchDao;

    private final RulesDao rulesDao;

    private final GameDao gameDao;

    public TournamentController(TournamentDao tournamentDao, MatchDao matchDao, RulesDao rulesDao, GameDao gameDao) {
        this.tournamentDao = tournamentDao;
        this.matchDao = matchDao;
        this.rulesDao = rulesDao;
        this.gameDao = gameDao;
    }

    @RequestMapping("/tournament")
    public String list(Model model) {

        model.addAttribute("list", tournamentDao.getAllTournaments());

        return "tournament/list";
    }

    @RequestMapping("/tournament/{id}")
    public String show(@PathVariable("id") String id, Model model) {

        Tournament tournament = tournamentDao.getTournamentById(id);

        String tournamentName;

        if (tournament == null) {
            tournament = new Tournament();
            tournamentName = "Nie znaleziono turnieju";
        } else tournamentName = tournament.getName();

        model.addAttribute("tournamentName", tournamentName);
        model.addAttribute("tournament", tournament);

        model.addAttribute("tournamentMatches", matchDao.getByTournamentId(tournament.getId()));

        return "tournament/show";
    }

    @RequestMapping("/tournament/add")
    public String addTournament(Model model) {

        model.addAttribute("newTournament", new Tournament());
        model.addAttribute("addedMessage", "");

        return "tournament/add";
    }

    @RequestMapping(value = "/tournament/add", method = RequestMethod.POST)
    public String addTournamentPost(@ModelAttribute Tournament tournament, Model model) {

        String gameId;

        try {
            gameId = gameDao.getGameByName(tournament.getGameName()).getId();
        } catch (Exception e) {
            model.addAttribute("addedMessage", "Podana gra nie znajduję się w bazie");
            model.addAttribute("newLeague", new League());
            return "tournament/add";
        }

        if (gameId == null || gameId.equals("")) {
            model.addAttribute("addedMessage", "Podana gra nie znajduję się w bazie");
            model.addAttribute("newLeague", new League());
            return "tournament/add";
        }

        tournament.setIdGame(gameId);

        boolean addNewRule = false;

        for (Rule rule2 : rulesDao.getAllRules()) {
            if (rule2.getDescription().equals(tournament.getRule())) {
                addNewRule = false;
                tournament.setRule(rule2.getId());
                break;
            }
            addNewRule = true;
        }

        if (addNewRule) {
            Rule rule = new Rule();
            rule.setDescription(tournament.getRule());
            rulesDao.addRule(rule);

            for (Rule rule1 : rulesDao.getAllRules()) {
                if (rule1.getDescription().equals(tournament.getRule()))

                    tournament.setRule(rule1.getId());
            }
        }

        int response = tournamentDao.addTournament(tournament);
        if (response == 0)
            model.addAttribute("addedMessage", "Turniej został dodany pomyślnie.");
        else model.addAttribute("addedMessage", "Nie udało sie dodać turnieju");
        model.addAttribute("newTournament", new Tournament());

        return "tournament/add";
    }
}
