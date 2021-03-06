package li.cil.oc.util

import net.minecraft.nbt._
import scala.language.implicitConversions
import scala.reflect.ClassTag

object ExtendedNBT {

  implicit def toNbt(value: Byte) = new NBTTagByte(null, value)

  implicit def toNbt(value: Short) = new NBTTagShort(null, value)

  implicit def toNbt(value: Int) = new NBTTagInt(null, value)

  implicit def toNbt(value: Array[Int]) = new NBTTagIntArray(null, value)

  implicit def toNbt(value: Long) = new NBTTagLong(null, value)

  implicit def toNbt(value: Float) = new NBTTagFloat(null, value)

  implicit def toNbt(value: Double) = new NBTTagDouble(null, value)

  implicit def toNbt(value: Array[Byte]) = new NBTTagByteArray(null, value)

  implicit def toNbt(value: String) = new NBTTagString(null, value)

  implicit def byteIterableToNbt(value: Iterable[Byte]) = value.map(toNbt)

  implicit def shortIterableToNbt(value: Iterable[Short]) = value.map(toNbt)

  implicit def intIterableToNbt(value: Iterable[Int]) = value.map(toNbt)

  implicit def intArrayIterableToNbt(value: Iterable[Array[Int]]) = value.map(toNbt)

  implicit def longIterableToNbt(value: Iterable[Long]) = value.map(toNbt)

  implicit def floatIterableToNbt(value: Iterable[Float]) = value.map(toNbt)

  implicit def doubleIterableToNbt(value: Iterable[Double]) = value.map(toNbt)

  implicit def byteArrayIterableToNbt(value: Iterable[Array[Byte]]) = value.map(toNbt)

  implicit def stringIterableToNbt(value: Iterable[String]) = value.map(toNbt)

  implicit def extendNBTTagCompound(nbt: NBTTagCompound) = new ExtendedNBTTagCompound(nbt)

  implicit def extendNBTTagList(nbt: NBTTagList) = new ExtendedNBTTagList(nbt)

  class ExtendedNBTTagCompound(val nbt: NBTTagCompound) {
    def setNewCompoundTag(name: String, f: (NBTTagCompound) => Any) = {
      val t = new NBTTagCompound()
      f(t)
      nbt.setCompoundTag(name, t)
      nbt
    }

    def setNewTagList(name: String, values: Iterable[NBTBase]) = {
      val t = new NBTTagList()
      t.append(values)
      nbt.setTag(name, t)
      nbt
    }

    def setNewTagList(name: String, values: NBTBase*): NBTTagCompound = setNewTagList(name, values)
  }

  class ExtendedNBTTagList(val nbt: NBTTagList) {
    def appendNewCompoundTag(f: (NBTTagCompound) => Unit) {
      val t = new NBTTagCompound()
      f(t)
      nbt.appendTag(t)
    }

    def append(values: Iterable[NBTBase]) {
      for (value <- values) {
        nbt.appendTag(value)
      }
    }

    def append(values: NBTBase*): Unit = append(values)

    def iterator[Tag <: NBTBase : ClassTag] = (0 until nbt.tagCount).map(nbt.tagAt).collect {
      case tag: Tag => tag
    }

    def foreach[Tag <: NBTBase : ClassTag](f: (Tag) => Unit) = iterator[Tag].foreach(f)

    def map[Tag <: NBTBase : ClassTag, Value](f: (Tag) => Value) = iterator[Tag].map(f)
  }

}
