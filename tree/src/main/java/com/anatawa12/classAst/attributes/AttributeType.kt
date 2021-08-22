package com.anatawa12.classAst.attributes

import com.anatawa12.classAst.ClassFileVersion
import kotlin.reflect.KClass

/**
 * Instance of this class indicates the kind of Attribute.
 * This should be base/parent class of companion object.
 * 
 * @constructor
 * Instantiate AttributeType.
 * 
 * @param implementationKClass The KClass instance indicates implementation class of this Attribute.
 * @param name The name of attribute in class file.
 * @param sinceVersion The minimal class file version this attribute can be appeared.
 * @param targets The set of location of this attribute. Empty if it's allowed to place on anywhere.
 */
abstract class AttributeType(
    /**
     * The KClass indicates implementation class of this Attribute.
     */
    val implementationKClass: KClass<out Attribute>,

    /**
     * The name of attribute in class file.
     */
    val name: String,

    /**
     * The minimal class file version this attribute can be appeared.
     */
    val sinceVersion: ClassFileVersion,
    targets: Collection<AttributeTarget>
) {
    /**
     * The Class indicates implementation class of this Attribute.
     * The value be always same to `implementationKClass.java`
     */
    val implementationClass: Class<out Attribute> get() = implementationKClass.java

    /**
     * The set of location of this attribute. Empty if it's allowed to place on anywhere.
     */
    val targets: Set<AttributeTarget>

    init {
        check(implementationKClass !in setOf(Attribute::class, GeneralAttribute::class))
        this.targets = targets.toSet()
    }

    /**
     * Returns the simple text explains name, minimal class file version and location of this attribute
     * @return The simple text explains name, minimal class file version and location of this attribute
     */
    final override fun toString(): String = "AttributeType($name, " +
            "since $sinceVersion " +
            "for ${targets.takeUnless { it.isEmpty() } ?: "anywhere"})"

    /**
     * Returns identity equals
     * @return identity equals
     */
    final override fun equals(other: Any?): Boolean = this === other

    /**
     * Returns identity hash code
     * @return identity hash code
     */
    final override fun hashCode(): Int = super.hashCode()
}
