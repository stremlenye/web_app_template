
DROP USER IF EXISTS url_shortener_user;
CREATE USER url_shortener_user WITH LOGIN PASSWORD '${DB_API_USER_PASS}';

REVOKE ALL ON ALL TABLES IN SCHEMA public FROM PUBLIC;
GRANT USAGE ON SCHEMA public to url_shortener_user;
