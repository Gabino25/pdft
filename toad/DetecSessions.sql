

SELECT s.sid,
       l.lock_type,
       l.mode_held,
       l.mode_requested,
       l.lock_id1,
       'alter system kill session '''|| s.sid|| ','|| s.serial#|| ''' immediate;' kill_sid
FROM   dba_lock_internal l,
       v$session s
WHERE  s.sid = l.session_id
AND    UPPER(l.lock_id1) LIKE '%MYP%'
AND    l.lock_type = 'Body Definition Lock'

alter system kill session '1140,64955' immediate;

alter system kill session '1928,62265' immediate;

alter system kill session '1931,55947' immediate;