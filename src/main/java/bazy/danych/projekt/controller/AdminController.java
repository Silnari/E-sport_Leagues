package bazy.danych.projekt.controller;

import bazy.danych.projekt.bean.Game;
import bazy.danych.projekt.bean.Match;
import bazy.danych.projekt.bean.PlayerInTeam;
import bazy.danych.projekt.bean.TeamInLeague;
import bazy.danych.projekt.dao.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AdminController {

    private final GameDao gameDao;

    private final PlayerInTeamDao playerInTeamDao;

    private final TeamDao teamDao;

    private final PlayerDao playerDao;

    private final TeamInLeagueDao teamInLeagueDao;

    private final LeagueDao leagueDao;

    private final MatchDao matchDao;

    private final TournamentDao tournamentDao;

    public AdminController(GameDao gameDao, PlayerInTeamDao playerInTeamDao, TeamDao teamDao, PlayerDao playerDao, TeamInLeagueDao teamInLeagueDao, LeagueDao leagueDao, MatchDao matchDao, TournamentDao tournamentDao) {
        this.gameDao = gameDao;
        this.playerInTeamDao = playerInTeamDao;
        this.teamDao = teamDao;
        this.playerDao = playerDao;
        this.teamInLeagueDao = teamInLeagueDao;
        this.leagueDao = leagueDao;
        this.matchDao = matchDao;
        this.tournamentDao = tournamentDao;
    }

    @RequestMapping("/admin")
    public String home(Model model) {

        return "admin/home";
    }

    @RequestMapping("/admin/add/teamMember")
    public String addTeamMember(Model model) {

        model.addAttribute("newTeamMember", new PlayerInTeam());
        model.addAttribute("addedMessage", "");

        return "admin/add/teamMember";
    }

    @RequestMapping(value = "/admin/add/teamMember", method = RequestMethod.POST)
    public String addTeamMemberPost(@ModelAttribute PlayerInTeam playerInTeam, Model model) {

        String playerId, teamId;

        try {
            playerId = playerDao.getPlayerByNickname(playerInTeam.getPlayerName()).getId();
        } catch (Exception e) {
            model.addAttribute("addedMessage", "Podany użytkownik nie znajduję się w bazie");
            model.addAttribute("newTeamMember", new PlayerInTeam());
            return "admin/add/teamMember";
        }

        try {
            teamId = teamDao.getTeamByName(playerInTeam.getTeamName()).getId();
        } catch (Exception e) {
            model.addAttribute("addedMessage", "Podany zespół nie znajduję się w bazie");
            model.addAttribute("newTeamMember", new PlayerInTeam());
            return "admin/add/teamMember";
        }

        if (playerInTeamDao.checkIfPlayerIsInTeam(playerId, teamId)) {
            model.addAttribute("addedMessage", "Podany użytkownik już znajduję się w drużynie");
            model.addAttribute("newTeamMember", new PlayerInTeam());
            return "admin/add/teamMember";
        }

        int response = playerInTeamDao.addPlayerInTeam(playerInTeam);
        if (response == 0)
            model.addAttribute("addedMessage", "Gracz został pomyślnie dodany do zespołu");
        else model.addAttribute("addedMessage", "Nie udało sie dodać gracza do zespołu");
        model.addAttribute("newTeamMember", new PlayerInTeam());

        return "admin/add/teamMember";
    }

    @RequestMapping("/admin/add/match")
    public String addMatch(Model model) {

        model.addAttribute("newMatch", new Match());
        model.addAttribute("addedMessage", "");

        return "admin/add/match";
    }

    @RequestMapping(value = "/admin/add/match", method = RequestMethod.POST)
    public String addMatchPost(@ModelAttribute Match match, Model model) {

        String team1Id, team2Id, gameId, leagueId = "", tournamentId = "";

        boolean leagueEmpty = match.getLeagueName().equals("");
        boolean tournamentEmpty = match.getTournamentName().equals("");

        if (leagueEmpty && tournamentEmpty) {
            model.addAttribute("addedMessage", "Mecz musi być ligowy lub turniejowy");
            model.addAttribute("newMatch", new Match());
            return "admin/add/match";
        }

        if (!leagueEmpty && !tournamentEmpty) {
            model.addAttribute("addedMessage", "Mecz nie może być ligowy oraz turniejowy");
            model.addAttribute("newMatch", new Match());
            return "admin/add/match";
        }

        if (!leagueEmpty) {
            try {
                leagueId = leagueDao.getLeagueByName(match.getLeagueName()).getId();
            } catch (Exception e) {
                model.addAttribute("addedMessage", "Podana liga nie znajduję się w bazie");
                model.addAttribute("newTeamInLeague", new TeamInLeague());
                return "admin/add/match";
            }
        }

        if (!tournamentEmpty) {
            try {
                tournamentId = tournamentDao.getTournamentByName(match.getTournamentName()).getId();
            } catch (Exception e) {
                model.addAttribute("addedMessage", "Podany turniej nie znajduję się w bazie");
                model.addAttribute("newTeamInLeague", new TeamInLeague());
                return "admin/add/match";
            }
        }

        try {
            gameId = gameDao.getGameByName(match.getGameName()).getId();
        } catch (Exception e) {
            model.addAttribute("addedMessage", "Podana gra nie znajduję się w bazie");
            model.addAttribute("newMatch", new Match());
            return "admin/add/match";
        }

        try {
            team1Id = teamDao.getTeamByName(match.getNameTeam1()).getId();
            team2Id = teamDao.getTeamByName(match.getNameTeam2()).getId();
        } catch (Exception e) {
            model.addAttribute("addedMessage", "Podana drużyna nie znajduję się w bazie");
            model.addAttribute("newMatch", new Match());
            return "admin/add/match";
        }

        if (!leagueEmpty) {
            if (!teamInLeagueDao.checkIfTeamIsInLeague(team1Id, leagueId)) {
                model.addAttribute("addedMessage", "Pierwsza drużyna nie znajduję się w lidze");
                model.addAttribute("newMatch", new Match());
                return "admin/add/match";
            }
            if (!teamInLeagueDao.checkIfTeamIsInLeague(team2Id, leagueId)) {
                model.addAttribute("addedMessage", "Druga drużyna nie znajduję się w lidze");
                model.addAttribute("newMatch", new Match());
                return "admin/add/match";
            }
        }

        try {
            int points1 = Integer.parseInt(match.getTeam1Points());
            int points2 = Integer.parseInt(match.getTeam2Points());

            if (points1 < 0 || points2 < 0) throw new Exception("Punkty muszą być większe od zera");

            if (points1 == points2) {
                model.addAttribute("addedMessage", "Liczba punktów nie może być taka sama");
                model.addAttribute("newMatch", new Match());
                return "admin/add/match";
            }
        } catch (Exception e) {
            model.addAttribute("addedMessage", "Podano niepoprawny format punktów");
            model.addAttribute("newMatch", new Match());
            return "admin/add/match";
        }

        match.setIdTeam1(team1Id);
        match.setIdTeam2(team2Id);
        match.setIdGame(gameId);
        match.setLeagueName(leagueId);
        match.setTournamentName(tournamentId);
        int response;

        if (leagueEmpty)
            response = matchDao.addTournamentMatch(match);
        else response = matchDao.addLeagueMatch(match);

        if (response == 0)
            model.addAttribute("addedMessage", "Mecz został dodany do bazy");
        else model.addAttribute("addedMessage", "Nie udało się dodać meczu do bazy");
        model.addAttribute("newMatch", new Match());

        return "admin/add/match";
    }

    @RequestMapping("/admin/add/leagueTeam")
    public String addLeagueTeam(Model model) {

        model.addAttribute("newTeamInLeague", new TeamInLeague());
        model.addAttribute("addedMessage", "");

        return "admin/add/leagueTeam";
    }

    @RequestMapping(value = "/admin/add/leagueTeam", method = RequestMethod.POST)
    public String addLeagueTeamPost(@ModelAttribute TeamInLeague teamInLeague, Model model) {

        String teamId, leagueId;

        try {
            teamId = teamDao.getTeamByName(teamInLeague.getId_team()).getId();
        } catch (Exception e) {
            model.addAttribute("addedMessage", "Podana drużyna nie znajduję się w bazie");
            model.addAttribute("newTeamInLeague", new TeamInLeague());
            return "admin/add/leagueTeam";
        }

        try {
            leagueId = leagueDao.getLeagueByName(teamInLeague.getId_league()).getId();
        } catch (Exception e) {
            model.addAttribute("addedMessage", "Podana liga nie znajduję się w bazie");
            model.addAttribute("newTeamInLeague", new TeamInLeague());
            return "admin/add/leagueTeam";
        }

        if (teamInLeagueDao.checkIfTeamIsInLeague(teamId, leagueId)) {
            model.addAttribute("addedMessage", "Podana drużyna już znajduję się w lidze");
            model.addAttribute("newTeamInLeague", new TeamInLeague());
            return "admin/add/leagueTeam";
        }

        teamInLeague.setId_league(leagueId);
        teamInLeague.setId_team(teamId);

        if (teamInLeague.getPoints().equals(""))
            teamInLeague.setPoints("0");

        if (teamInLeague.getNumberOfWins().equals(""))
            teamInLeague.setNumberOfWins("0");

        if (teamInLeague.getNumberOfLosses().equals(""))
            teamInLeague.setNumberOfLosses("0");

        int response = teamInLeagueDao.addTeamInLeague(teamInLeague);
        if (response == 0)
            model.addAttribute("addedMessage", "Drużyna została pomyślnie dodana do ligi");
        else model.addAttribute("addedMessage", "Nie udało się dodać drużyny do ligi");
        model.addAttribute("newTeamInLeague", new TeamInLeague());

        return "admin/add/leagueTeam";
    }

    @RequestMapping("/admin/add/game")
    public String addGame(Model model) {

        model.addAttribute("newGame", new Game());
        model.addAttribute("addedMessage", "");

        return "admin/add/game";
    }

    @RequestMapping(value = "/admin/add/game", method = RequestMethod.POST)
    public String addGamePost(@ModelAttribute Game game, Model model) {

        int response = gameDao.addGame(game);
        if (response == 0)
            model.addAttribute("addedMessage", "Gra została dodana pomyślnie.");
        else model.addAttribute("addedMessage", "Nie udało sie dodać gry");
        model.addAttribute("newGame", new Game());

        return "admin/add/game";
    }
}
