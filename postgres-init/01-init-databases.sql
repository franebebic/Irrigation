DO $$
BEGIN
  IF NOT EXISTS (SELECT FROM pg_database WHERE datname = 'keycloak') THEN
    CREATE DATABASE keycloak;
END IF;

  IF NOT EXISTS (SELECT FROM pg_database WHERE datname = 'irrigation') THEN
    CREATE DATABASE irrigation;
END IF;
END $$;
