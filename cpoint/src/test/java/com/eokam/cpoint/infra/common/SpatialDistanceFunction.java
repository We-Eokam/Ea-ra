package com.eokam.cpoint.infra.common;

import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

public class SpatialDistanceFunction {

	public static Point POINT(final Double latitude, final Double longitude) {
		return wktToPoint(latitude, longitude);
	}

	public static double ST_Distance_Sphere(Point p1, Point p2) {
		Double lon1 = p1.getX();
		Double lon2 = p2.getX();
		Double lat1 = p1.getY();
		Double lat2 = p2.getY();

		int R = 6371000;
		double dLat = Math.toRadians(lat2 - lat1);
		double dLon = Math.toRadians(lon2 - lon1);
		lat1 = Math.toRadians(lat1);
		lat2 = Math.toRadians(lat2);

		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
			Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		return R * c;
	}

	private static Point wktToPoint(final Double latitude, final Double longitude) {
		final String wellKnownText = String.format("POINT(%f %f)", longitude, latitude);
		try {
			return (Point)new WKTReader().read(wellKnownText);
		} catch (ParseException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}
}