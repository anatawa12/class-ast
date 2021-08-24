package com.anatawa12.classAst.annotation

/**
 * The `target_path` structure.
 * @spec 4.7.20.1
 */
class TypePath(val path: MutableList<TypePathEntry>) {
    override fun toString(): String {
        return "TypePath[${path.joinToString(".")}]"
    }

    class Builder {
        private val building: MutableList<TypePathEntry>

        constructor() {
            building = ArrayList()
        }

        constructor(initialCount: Int) {
            building = ArrayList(initialCount)
        }

        fun array() = apply { building.add(TypePathEntry.DeeperInArray) }
        fun nested() = apply { building.add(TypePathEntry.DeeperInNested) }
        fun bound(boundIndex: UByte) = apply { building.add(TypePathEntry.BoundOfWildcardTypeArgument(boundIndex)) }
        fun argument(index: UByte) = apply { building.add(TypePathEntry.TypeArgument(index)) }
    }

    companion object {
        fun builder() = Builder()
        fun builder(initialCount: Int) = Builder(initialCount)
    }
}

/**
 * The entry of `path` field of `target_path` structure.
 * @spec 4.7.20.1
 */
sealed class TypePathEntry {
    /**
     * This corresponds Annotation is deeper in an array type
     */
    object DeeperInArray : TypePathEntry() {
        override fun toString(): String = "array"
    }

    /**
     * This corresponds Annotation is deeper in a nested type
     */
    object DeeperInNested : TypePathEntry() {
        override fun toString(): String = "nested"
    }

    /**
     * Instance of this class corresponds Annotation is on the bound of 
     * a wildcard type argument of a parameterized type
     */
    class BoundOfWildcardTypeArgument(val boundIndex: UByte) : TypePathEntry() {
        override fun toString(): String = "bound#$boundIndex"

        override fun equals(other: Any?): Boolean {
            return this === other ||
                    (javaClass == other?.javaClass
                            && boundIndex == (other as BoundOfWildcardTypeArgument).boundIndex)
        }

        override fun hashCode(): Int {
            return boundIndex.hashCode()
        }
    }

    /**
     * Instance of this class corresponds Annotation is on a type argument of a parameterized type
     */
    class TypeArgument(val index: UByte) : TypePathEntry(){
        override fun toString(): String = "argument#$index"

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as BoundOfWildcardTypeArgument

            if (index != other.boundIndex) return false

            return true
        }

        override fun hashCode(): Int {
            return index.hashCode()
        }
    }
}
