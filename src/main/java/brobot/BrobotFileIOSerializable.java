package brobot;

@FunctionalInterface
/**
 * This functional interface has a single abstract method that allows all implementing types
 * to serialize instances by custom BroBot domain rules so that they can be written to a file.
 */
public interface BrobotFileIOSerializable extends BrobotSerializable {
    /**
     * @return
     * A serialized File-IO friendly serialization String as per Brobot Domain Rules.
     */
    String toFileReport();
}
