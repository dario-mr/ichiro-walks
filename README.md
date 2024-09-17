# Ichiro walks

Web app to track the walks of our dog Ichiro

## Environment variables

- `PORT`: server port on which to run the app (default: `8085`)
- `PROFILE`: spring profile to apply (default: `prod`)
    - `dev`: vaadin production mode is off
    - `prod`: vaadin production mode is on
- `DB_PASSWORD`: database password (default: `<empty>`)
- `TIMEZONE`: app time-zone (default: `UTC+2`)
    - this is a work-around until I find a proper solution to handle timezones in docker (never)
