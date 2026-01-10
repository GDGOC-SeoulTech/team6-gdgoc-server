package com.gdgoc.arcive.domain.member.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
public enum Major {
    COMPUTER_ENGINEERING("컴퓨터공학과"),
    MECHANICAL_SYSTEM_DESIGN_ENGINEERING("기계시스템디자인공학과"),
    MECHANICAL_AUTOMOTIVE_ENGINEERING("기계자동차공학과"),
    SAFETY_ENGINEERING("안전공학과"),
    MATERIALS_SCIENCE_AND_ENGINEERING("신소재공학과"),
    CIVIL_SYSTEMS_ENGINEERING("건설시스템공학과"),
    ARCHITECTURE_ARCHITECTURAL_ENGINEERING("건축학부(건축공학전공)"),
    ARCHITECTURE("건축학부(건축학전공)"),
    ARCHITECTURAL_MECHANICAL_FACILITY_ENGINEERING("건축기계설비공학과"),
    MECHANICAL_SYSTEM_ENGINEERING("기계시스템공학부"),
    MECHANICAL_ENGINEERING("기계공학과"),
    ELECTRICAL_AND_INFORMATION_ENGINEERING("전기정보공학과"),
    ELECTRONIC_ENGINEERING("전자공학과"),
    SMART_ICT_CONVERGENCE_ENGINEERING("스마트ICT융합공학과"),
    CHEMICAL_AND_BIOLOGICAL_ENGINEERING("화공생명공학과"),
    ENVIRONMENTAL_ENGINEERING("환경공학과"),
    FOOD_SCIENCE_AND_BIOTECHNOLOGY("식품생명공학과"),
    FINE_CHEMISTRY("정밀화학과"),
    SPORTS_SCIENCE("스포츠과학과"),
    OPTOMETRY("안경광학과"),
    BIOMEDICAL_ENGINEERING("바이오메디컬학과"),
    DESIGN_INDUSTRIAL_DESIGN("디자인학과(산업디자인전공)"),
    DESIGN_VISUAL_COMMUNICATION_DESIGN("디자인학과(시각디자인전공)"),
    CERAMICS("도예학과"),
    METAL_CRAFT_DESIGN("금속공예디자인학과"),
    FINE_ARTS("조형예술학과"),
    PUBLIC_ADMINISTRATION("행정학과"),
    ENGLISH_LANGUAGE_AND_LITERATURE("영어영문학과"),
    CREATIVE_WRITING("문예창작학과"),
    INDUSTRIAL_ENGINEERING_IIS("산업공학과(산업정보시스템전공)"),
    INDUSTRIAL_ENGINEERING_ITM("산업공학과(ITM전공)"),
    MSDE("MSDE학과"),
    BUSINESS_ADMINISTRATION("경영학과(경영학전공)"),
    BUSINESS_ADMINISTRATION_GLOBAL_TECHNO("경영학과(글로벌테크노경영전공)"),
    CONVERGENCE_MECHANICAL_ENGINEERING("융합기계공학과"),
    CIVIL_AND_ENVIRONMENTAL_CONVERGENCE_ENGINEERING("건설환경융합공학과"),
    HEALTH_FITNESS("헬스피트니스학과"),
    CULTURE_AND_ARTS("문화예술학과"),
    ENGLISH("영어과"),
    VENTURE_MANAGEMENT("벤처경영학과"),
    INFORMATION_AND_COMMUNICATION_CONVERGENCE_ENGINEERING("정보통신융합공학과"),
    ARTIFICIAL_INTELLIGENCE_APPLICATION("인공지능응용학과"),
    INTELLIGENT_SEMICONDUCTOR_ENGINEERING("지능형반도체공학과"),
    FUTURE_ENERGY_CONVERGENCE("미래에너지융합학과");

    private final String description;

    private static final Map<String, Major> DESCRIPTION_MAP =
            Arrays.stream(values())
                    .collect(Collectors.toMap(
                            Major::getDescription,
                            Function.identity()
                    ));


    public String getDescription() {
        return description;
    }

    public static Major fromDescription(String description) {
        Major major = DESCRIPTION_MAP.get(description);
        if (major == null) {
            throw new IllegalArgumentException("Unknown major: " + description);
        }
        return major;
    }
}