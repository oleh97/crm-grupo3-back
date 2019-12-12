package com.anonymous.crm.repository;

import com.anonymous.crm.model.Youtuber;
import com.anonymous.crm.model.YoutuberDto;
import com.anonymous.crm.model.graphs.CountriesVisualization;
import com.anonymous.crm.model.graphs.ServiceEarnings;
import com.anonymous.crm.model.graphs.YoutuberEarnings;
import org.hibernate.query.criteria.internal.expression.function.AggregationFunction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface YoutuberRepository extends JpaRepository<Youtuber, Integer> {

    @Query("SELECT Y from Youtuber Y LEFT OUTER JOIN Y.youtuberDetails D " +
            "JOIN D.geographicZone G " +
            "JOIN D.ageRange A " +
            "WHERE (" +
                "D.contentType = :contentType AND " +
                "D.visitRange = :visitRange AND " +
                "(KEY(G) = :geographicalZone AND G >= 15) AND " +
                "(KEY(A) = :ageRange AND A >= 15))"
    )
    List<Youtuber> getYoutubersForCampaign(@Param("contentType") String contentType,
                                           @Param("geographicalZone") String geographicalZone,
                                           @Param("ageRange") String ageRange,
                                           @Param("visitRange") String visitRange);

    @Query("SELECT NEW com.anonymous.crm.model.graphs.YoutuberEarnings(Y.channelName, SUM (V.monetization), SUM (V.views)) FROM Youtuber Y INNER JOIN Y.videos V GROUP BY Y.id ORDER BY SUM (V.monetization) DESC")
    List<YoutuberEarnings> getTopYoutubers(Pageable pageable);

    @Query("SELECT NEW com.anonymous.crm.model.graphs.ServiceEarnings(SUM (D.coursesSpending), SUM(D.editionSpending), SUM (D.miniatureSpending)) FROM Youtuber Y INNER JOIN Y.youtuberDetails D")
    ServiceEarnings getCompanyEarningsWithServices();

    @Query("SELECT " +
            "NEW com.anonymous.crm.model.graphs.CountriesVisualization(" +
            "COUNT (Y)," +
            "SUM (Spain)," +
            " SUM (France)," +
            " SUM (Andorra)," +
            " SUM (UnitedKingdom)," +
            " SUM (UnitedStatesOfAmerica)," +
            " SUM (SouthAmerica)," +
            " SUM (China)," +
            " SUM (Japan)) " +
            "FROM Youtuber Y LEFT OUTER JOIN Y.youtuberDetails D " +
            "JOIN D.geographicZone Spain " +
            "JOIN D.geographicZone France " +
            "JOIN D.geographicZone Andorra " +
            "JOIN D.geographicZone UnitedKingdom " +
            "JOIN D.geographicZone UnitedStatesOfAmerica " +
            "JOIN D.geographicZone SouthAmerica " +
            "JOIN D.geographicZone China " +
            "JOIN D.geographicZone Japan " +
            "WHERE KEY(Spain) = 'Spain' " +
            "AND KEY(France) = 'France' " +
            "AND KEY(Andorra) = 'Andorra' " +
            "AND KEY(UnitedKingdom) = 'United Kingdom' " +
            "AND KEY(UnitedStatesOfAmerica) = 'United States of America' " +
            "AND KEY(SouthAmerica) = 'South America' " +
            "AND KEY(China) = 'China' " +
            "AND KEY(Japan) = 'Japan'")
    CountriesVisualization getCompanyCountriesVisualization();


}
