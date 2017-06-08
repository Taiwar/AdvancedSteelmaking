package net.muellersites.advancedsteelmaking.block.block;

public interface IEnumMeta {

    int getMeta();

    default String getName() {
        return ((Enum) this).name().toLowerCase();
    }

}
