package net.muellersites.advancedsteelmaking.block.base;

public interface IEnumMeta {

    int getMeta();

    default String getName() {
        return ((Enum) this).name().toLowerCase();
    }

}
