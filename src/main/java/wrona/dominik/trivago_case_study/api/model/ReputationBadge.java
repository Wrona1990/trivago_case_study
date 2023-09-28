package wrona.dominik.trivago_case_study.api.model;

/**
 * ReputationBadge Enum
 * Value is based on {@link Item#getReputation()}
 * If reputation is <= 500 the value is RED
 * If reputation is <= 799 the value is YELLOW
 * Otherwise the value is GREEN.
 */
public enum ReputationBadge {
    GREEN,
    YELLOW,
    RED
}
