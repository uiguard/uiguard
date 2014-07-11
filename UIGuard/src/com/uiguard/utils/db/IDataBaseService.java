package com.uiguard.utils.db;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.uiguard.exception.UiGuardException;

public interface IDataBaseService {

	public abstract Connection getDataBaseConnection(final String prefix)
			throws UiGuardException;

	public abstract List<Map<String, Object>> query(final String prefix,
			final String sql) throws UiGuardException;

	public abstract boolean execute(final String prefix, final String sql)
			throws UiGuardException;

	public abstract void closeConnections();

}