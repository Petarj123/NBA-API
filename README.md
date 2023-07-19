**NBA API**

**This API provides detailed statistics and data from the NBA.

Endpoints**

**LeadersController**

GET /api/v1/leaders/pts: Retrieves the list of scoring leaders. An optional parameter top can be provided to limit the number of players returned.

GET /api/v1/leaders/ast: Retrieves the list of assist leaders. The top parameter can be used to limit the number of players returned.

GET /api/v1/leaders/stl: Retrieves the list of steals leaders. The top parameter can be used to limit the number of players returned.

GET /api/v1/leaders/reb: Retrieves the list of rebound leaders. The top parameter can be used to limit the number of players returned.

**PlayerController**

GET /api/v1/players/find: Returns player data. Required parameters are firstName and lastName.

**SeasonController**

GET /api/v1/seasons/champion: Retrieves the champion of a given season. The required parameter is season.

GET /api/v1/seasons/mvp: Retrieves the Most Valuable Player of a given season. The required parameter is season.

GET /api/v1/seasons/dpoy: Retrieves the Defensive Player of the Year of a given season. The required parameter is season.

GET /api/v1/seasons/smoy: Retrieves the Sixth Man of the Year of a given season. The required parameter is season.

GET /api/v1/seasons/mip: Retrieves the Most Improved Player of a given season. The required parameter is season.

GET /api/v1/seasons/roy: Retrieves the Rookie of the Year of a given season. The required parameter is season.

GET /api/v1/seasons/assist: Retrieves the assists champion of a given season. The required parameter is season.

GET /api/v1/seasons/scoring: Retrieves the scoring champion of a given season. The required parameter is season.

GET /api/v1/seasons/rebound: Retrieves the rebounding champion of a given season. The required parameter is season.

GET /api/v1/seasons/ws: Retrieves the player with the highest win shares of a given season. The required parameter is season.

**TeamController**

GET /api/v1/team/allTeams: Retrieves all teams.

GET /api/v1/team/conference: Retrieves teams in a given conference. The required parameter is conference.

GET /api/v1/team/division: Retrieves teams in a given division. The required parameter is division.

GET /api/v1/team/standings: Retrieves the season standings. The required parameter is season.

GET /api/v1/team/standings/conference: Retrieves the conference standings. The required parameter is season.

GET /api/v1/team/standings/divisions: Retrieves the division standings. The required parameter is season.

GET /api/v1/team/h2h: Retrieves the head-to-head score between two teams. Required parameters are team1 and team2.
